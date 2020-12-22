package chess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class Game {

  private val _board: MutableState<Board> = mutableStateOf(Board())
  val board: State<Board> get() = _board

  private val _validMoves: MutableState<List<Coordinate>> = mutableStateOf(emptyList())
  val validMoves: State<List<Coordinate>> get() = _validMoves

  fun reset() {
    _board.value = Board()
  }

  fun startMove(coordinate: Coordinate) {
    _validMoves.value = validMoves(coordinate)
  }

  private fun piece(coordinate: Coordinate): Piece? {
    return board.value[coordinate.row][coordinate.column]
  }

  private fun validMoves(coordinate: Coordinate): List<Coordinate> {
    val piece = piece(coordinate)
    return if (piece == null) {
      emptyList()
    } else {
      if (piece.isRook) {
        val validMove: (Coordinate) -> Boolean = { it.isValid && piece(it) == null }
        val positiveRowMoves = generateSequence(coordinate) {
          it.plusRows(1)
        }.drop(1).takeWhile(validMove)
        val negativeRowMoves = generateSequence(coordinate) {
          it.minusRows(1)
        }.drop(1).takeWhile(validMove)
        val positiveColumnMoves = generateSequence(coordinate) {
          it.minusColumns(1)
        }.drop(1).takeWhile(validMove)
        val negativeColumnMoves = generateSequence(coordinate) {
          it.plusColumns(1)
        }.drop(1).takeWhile(validMove)
        (positiveRowMoves + negativeRowMoves + positiveColumnMoves + negativeColumnMoves).toList()
      } else {
        listOf(Coordinate(row = coordinate.row - 1, column = coordinate.column))
      }
    }
  }

  fun stopMove() {
    _validMoves.value = emptyList()
  }

  fun move(from: Coordinate, to: Coordinate) {
    if (to !in validMoves(from)) {
      return
    }
    val mutableBoard = _board.value.toMutableList().map {
      it.toMutableList()
    }
    val removed = mutableBoard[from.row].set(from.column, null)
    if (removed != null) {
      mutableBoard[to.row][to.column] = removed
    }
    _board.value = mutableBoard
  }
}
