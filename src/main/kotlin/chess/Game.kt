package chess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class Game {

  private val _board: MutableState<Board> = mutableStateOf(Board())
  val board: State<Board> get() = _board

  fun reset() {
    _board.value = Board()
  }

  fun move(fromColumn: Int, fromRow: Int, toColumn: Int, toRow: Int) {
    val mutableBoard = _board.value.toMutableList().map {
      it.toMutableList()
    }
    val removed = mutableBoard[fromRow].set(fromColumn, null)
    if (removed != null) {
      mutableBoard[toRow][toColumn] = removed
    }
    _board.value = mutableBoard
  }
}
