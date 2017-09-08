package wangzx.commons.binlog


class Test1 {


  def test1(): Unit = {

    val entry: Entry = null
    val rowData: RowData = null

    entry match {
      case INSERT(schema, table, rows) =>
    }

    rowData match {
      case RowChangePattern(row"id%int=${id: Int}, name=${name0: String}", row"name=${name1: String}") =>
        println(s"record id=$id change from $name0 to $name1")
    }

    entry match {
      case INSERT(schema, table, RowChangePattern(_, row"id=1")) =>
      case UPDATE("crm", "companies", RowChangePattern(old: List[Column], nw: List[Column])) =>
    }

  }

}
