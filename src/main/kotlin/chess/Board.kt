package chess

typealias Board = List<List<Piece?>>

fun Board(whiteOnBottom: Boolean): Board {
  val whiteBoard = List(8) { column ->
    List(8) { row ->
      when (column) {
        0 -> when (row) {
          0 -> Piece.BlackRook
          1 -> Piece.BlackKnight
          2 -> Piece.BlackBishop
          3 -> Piece.BlackQueen
          4 -> Piece.BlackKing
          5 -> Piece.BlackBishop
          6 -> Piece.BlackKnight
          7 -> Piece.BlackRook
          else -> error("invalid row=$row")
        }
        1 -> {
          Piece.BlackPawn
        }
        6 -> {
          Piece.WhitePawn
        }
        7 -> when (row) {
          0 -> Piece.WhiteRook
          1 -> Piece.WhiteKnight
          2 -> Piece.WhiteBishop
          3 -> Piece.WhiteKing
          4 -> Piece.WhiteQueen
          5 -> Piece.WhiteBishop
          6 -> Piece.WhiteKnight
          7 -> Piece.WhiteRook
          else -> error("invalid row=$row")
        }
        else -> {
          null
        }
      }
    }
  }

  return if (whiteOnBottom) {
    whiteBoard
  } else {
    whiteBoard.reversed()
  }
}
