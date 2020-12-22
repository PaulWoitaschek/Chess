package chess

enum class Piece(
  val path: String,
  val isRook: Boolean = false,
  val isKnight: Boolean = false,
  val isKing: Boolean = false,
  val isQueen: Boolean = false,
  val isBishop: Boolean = false
) {
  BlackBishop("bishop_black.xml", isBishop = true),
  WhiteBishop("bishop_white.xml", isBishop = true),
  BlackKing("king_black.xml", isKing = true),
  WhiteKing("king_white.xml", isKing = true),
  BlackKnight("knight_black.xml", isKnight = true),
  WhiteKnight("knight_white.xml", isKnight = true),
  BlackPawn("pawn_black.xml"),
  WhitePawn("pawn_white.xml"),
  BlackQueen("queen_black.xml", isQueen = true),
  WhiteQueen("queen_white.xml", isQueen = true),
  BlackRook("rook_black.xml", isRook = true),
  WhiteRook("rook_white.xml", isRook = true),
}
