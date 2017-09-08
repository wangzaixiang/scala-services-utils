package wangzx.commons

package object binlog {

  object SourceType extends Enumeration {
    val ORACLE = Value(1)
    val MYSQL = Value(2)
    val PGSQL = Value(3)
  }

  object EventType extends Enumeration {
    val INSERT = Value(1)
    val UPDATE = Value(2)
    val DELETE = Value(3)
    val CREATE = Value(4)
    val ALTER = Value(5)
    val ERASE = Value(6)
    val QUERY = Value(7)
    val TRUNCATE = Value(8)
    val RENAME = Value(9)
    val CINDEX = Value(10)
    val DINDEX = Value(11)
  }

  object EntryType extends Enumeration {
    val TRANSACTIONBEGIN = Value(1)
    val ROWDATA = Value(2)
    val TRANSACTIONEND = Value(3)
    val HEARTBEAT = Value(4)
  }

  trait StoreValue
  case class TransactionBegin
  (
    executeTime: Long,
    transactionId: String,
    threadId: Long,
    props: Map[String,String] = Map()

  )extends StoreValue


  case class TransactionEnd
  (
    executeTime: Long,
    transactionId: String,
    props: Map[String,String] = Map()
  )extends StoreValue

  case class RowChange
  (
    tableId: String,
    eventType: EventType.Value,
    isDDL: Boolean,
    sql: String,
    rowDatas: List[RowData],
    ddlSchemaName: String,
    props: Map[String,String] = Map()
  ) extends StoreValue

  case object HeartBeat extends StoreValue

  case class RowData
  (
    before: List[Column],
    after: List[Column],
    props: Map[String,String] = Map()
  )

  case class Column
  (
    index: Int,
    sqlType: Int,
    name: String,
    isKey: Boolean,
    updated: Boolean,
    isNull: Boolean,
    value: String,
    length: Int,
    mysqlType: String,
    props: Map[String, String] = Map()
  )

  case class Header
  (
    version: Int,
    logfileName: String,
    logfileOffset: Long,
    serverId: Long,
    serverenCode: String,
    executeTime: Long,
    sourceType: SourceType.Value,
    schemaName: String,
    tableName: String,
    evenType: EventType.Value,
    props: Map[String, String] = Map()
  )

  case class Entry
  (
    header: Header,
    entryType: EntryType.Value,
    storeValue: StoreValue
  ) {
    // flattern a Entry of List[RowChange] to List[Entry]
    def flattern: List[Entry] = {
      storeValue match {
        case rc: RowChange if rc.rowDatas.size > 1 =>
          rc.rowDatas.map { data =>
            this.copy(storeValue = rc.copy(rowDatas = List(data)))
          }
        case _ => List(this)
      }
    }
  }

  // support simple grammar:
  // name=value, name%type=value
  // TODO: type: byte/short/int/float/double/bigdecimal
  implicit class recordInterpolation(sc: StringContext) {
    object row {
      def unapplySeq(arg: List[Column]): Option[Seq[Any]] = ???
    }
  }

  object RowChangePattern {
    def unapply(arg: RowData): Option[(List[Column], List[Column])] = Some((arg.before, arg.after))

    // only match 1-elem list
    def unapply(arg: List[RowData]): Option[(List[Column], List[Column])]  = ???
  }


  object INSERT {
    def unapply(arg: Entry): Option[(String, String, List[RowData])] = ???
  }

  object UPDATE {
    def unapply(arg: Entry): Option[(String, String, List[RowData])] = ???
  }

  object DELETE {
    def unapply(arg: Entry): Option[(String, String, List[RowData])] = ???
  }

}
