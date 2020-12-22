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

  private fun isValidMove(to: Coordinate): Boolean {
    return to.isValid && piece(to) == null
  }

  private fun validMoves(coordinate: Coordinate): List<Coordinate> {
    val piece = piece(coordinate)
    return if (piece == null) {
      emptyList()
    } else {
      when {
        piece.isRook -> validRookMoves(coordinate)
        piece.isKnight -> {
          listOf(
            coordinate.plus(2, 1),
            coordinate.plus(2, -1),
            coordinate.plus(-2, 1),
            coordinate.plus(-2, -1),
            coordinate.plus(1, 2),
            coordinate.plus(1, -2),
            coordinate.plus(-1, 2),
            coordinate.plus(-1, -2),
          ).filter(::isValidMove)
        }
        piece.isKing -> {
          listOf(
            coordinate.plus(1, -1),
            coordinate.plus(1, 0),
            coordinate.plus(1, 1),
            coordinate.plus(0, -1),
            coordinate.plus(0, 1),
            coordinate.plus(-1, -1),
            coordinate.plus(-1, 0),
            coordinate.plus(-1, 1),
          ).filter(::isValidMove)
        }
        piece.isQueen -> validRookMoves(coordinate)

        else -> {
          listOf(Coordinate(row = coordinate.row - 1, column = coordinate.column))
        }
      }
    }
  }

  private fun validRookMoves(coordinate: Coordinate): List<Coordinate> {
    val coordinateManipulations: List<(Coordinate) -> Coordinate> = listOf(
      { it.plusRows(1) },
      { it.minusRows(1) },
      { it.plusColumns(1) },
      { it.minusColumns(1) },
    )
    return coordinateManipulations.flatMap {
      generateSequence(coordinate, it).drop(1).takeWhile(::isValidMove)
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
