package chess

data class Coordinate(val row: Int, val column: Int) {

  fun plusColumns(value: Int): Coordinate = copy(column = column + value)

  fun minusColumns(value: Int): Coordinate = plusColumns(-value)

  fun plusRows(value: Int): Coordinate = copy(row = row + value)

  fun minusRows(value: Int): Coordinate = plusRows(-value)

  fun plus(rows: Int, columns: Int): Coordinate = copy(row = row + rows, column = column + columns)

  val isValid: Boolean = row in 0..7 && column in 0..7
}
