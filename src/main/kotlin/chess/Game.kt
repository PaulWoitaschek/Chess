package chess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class Game {

  val board: State<Board> get() = _board
  private var whiteOnBottom = true
  private var whiteTurn = true
  private val _board: MutableState<Board> = mutableStateOf(Board(whiteOnBottom = whiteOnBottom))

  private val _validMoves: MutableState<List<Coordinate>> = mutableStateOf(emptyList())
  val validMoves: State<List<Coordinate>> get() = _validMoves

  fun restartGame(whiteOnBottom: Boolean) {
    this.whiteOnBottom = whiteOnBottom
    _board.value = Board(whiteOnBottom = whiteOnBottom)
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
      if ((piece.isWhite && !whiteTurn) || (!piece.isWhite && whiteTurn)) {
        return emptyList()
      }
      when {
        piece.isRook -> validRookMoves(coordinate)
        piece.isKnight -> validKnightMoves(coordinate)
        piece.isKing -> validKingMoves(coordinate)
        piece.isQueen -> validRookMoves(coordinate) + validBishopMoves(coordinate)
        piece.isBishop -> validBishopMoves(coordinate)
        piece.isPawn -> validPawnMoves(coordinate, isWhitePiece = piece.isWhite)
        else -> error("Unhandled piece=$piece")
      }
    }
  }

  private fun validPawnMoves(coordinate: Coordinate, isWhitePiece: Boolean): List<Coordinate> {
    return if (isWhitePiece) {
      if (whiteOnBottom) {
        listOf(coordinate.minusRows(1)).filter(::isValidMove)
      } else {
        listOf(coordinate.plusRows(1)).filter(::isValidMove)
      }
    } else {
      if (whiteOnBottom) {
        listOf(coordinate.plusRows(1)).filter(::isValidMove)
      } else {
        listOf(coordinate.minusRows(1)).filter(::isValidMove)
      }
    }
  }

  private fun validKnightMoves(coordinate: Coordinate) = listOf(
    coordinate.plus(2, 1),
    coordinate.plus(2, -1),
    coordinate.plus(-2, 1),
    coordinate.plus(-2, -1),
    coordinate.plus(1, 2),
    coordinate.plus(1, -2),
    coordinate.plus(-1, 2),
    coordinate.plus(-1, -2),
  ).filter(::isValidMove)

  private fun validKingMoves(coordinate: Coordinate) = listOf(
    coordinate.plus(1, -1),
    coordinate.plus(1, 0),
    coordinate.plus(1, 1),
    coordinate.plus(0, -1),
    coordinate.plus(0, 1),
    coordinate.plus(-1, -1),
    coordinate.plus(-1, 0),
    coordinate.plus(-1, 1),
  ).filter(::isValidMove)

  private fun validBishopMoves(coordinate: Coordinate): List<Coordinate> {
    val coordinateManipulations: List<(Coordinate) -> Coordinate> = listOf(
      { it.plus(1, 1) },
      { it.plus(1, -1) },
      { it.plus(-1, 1) },
      { it.plus(-1, -1) },
    )
    return coordinateManipulations.flatMap {
      generateSequence(coordinate, it).drop(1).takeWhile(::isValidMove)
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
    whiteTurn = !whiteTurn
  }
}
