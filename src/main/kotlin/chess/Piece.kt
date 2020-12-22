package chess

enum class Piece(val path: String) {
  BlackBishop("bishop_black.xml"),
  WhiteBishop("bishop_white.xml"),
  BlackKing("king_black.xml"),
  WhiteKing("king_white.xml"),
  BlackKnight("knight_black.xml"),
  WhiteKnight("knight_white.xml"),
  BlackPawn("pawn_black.xml"),
  WhitePawn("pawn_white.xml"),
  BlackQueen("queen_black.xml"),
  WhiteQueen("queen_white.xml"),
  BlackRook("rook_black.xml"),
  WhiteRook("rook_white.xml"),
}
