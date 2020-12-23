package chess

enum class Piece(
  val path: String,
  val isWhite : Boolean,
  val isRook: Boolean = false,
  val isKnight: Boolean = false,
  val isKing: Boolean = false,
  val isQueen: Boolean = false,
  val isBishop: Boolean = false,
  val isPawn: Boolean = false
) {
  BlackBishop("bishop_black.xml", isBishop = true, isWhite = false),
  WhiteBishop("bishop_white.xml", isBishop = true, isWhite = true),
  BlackKing("king_black.xml", isKing = true, isWhite = false),
  WhiteKing("king_white.xml", isKing = true, isWhite = true),
  BlackKnight("knight_black.xml", isKnight = true, isWhite = false),
  WhiteKnight("knight_white.xml", isKnight = true, isWhite = true),
  BlackPawn("pawn_black.xml", isPawn = true, isWhite = false),
  WhitePawn("pawn_white.xml", isPawn = true, isWhite = true),
  BlackQueen("queen_black.xml", isQueen = true, isWhite = false),
  WhiteQueen("queen_white.xml", isQueen = true, isWhite = true),
  BlackRook("rook_black.xml", isRook = true, isWhite = false),
  WhiteRook("rook_white.xml", isRook = true, isWhite = true),
}
