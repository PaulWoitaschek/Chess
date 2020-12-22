package chess

import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


fun main() {
  val itemSize = 120.dp
  Window(title = "Compose for Desktop", size = IntSize(1200, 1200)) {
    val game = Game()
    MaterialTheme {
      Column {
        Button(onClick = {
          game.reset()
        }) {
          Text("Restart")
        }
        game.board.value.forEachIndexed { rowIndex, column ->
          val evenRow = rowIndex % 2 == 0
          Row {
            column.forEachIndexed { columnIndex, chessPiece ->
              val evenColumn = columnIndex % 2 == 0
              val blackField = (evenRow && !evenColumn) || (evenColumn && !evenRow)
              val color = if (blackField) Color(0xFF616161) else Color(0xffbdbdbd)
              Box(Modifier.width(itemSize)
                .background(color = color)
                .height(itemSize)
              ) {
                if (chessPiece != null) {
                  ChessPieceImage(chessPiece) { offset ->
                    val moveX = (offset.x.dp / itemSize)
                      .roundToInt()
                    val moveY = (offset.y.dp / itemSize)
                      .roundToInt()
                    game.move(
                      fromColumn = columnIndex,
                      fromRow = rowIndex,
                      toColumn = columnIndex + moveX,
                      toRow = rowIndex + moveY
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Suppress("FunctionName")
@Composable
fun BoxScope.ChessPieceImage(
  type: Piece,
  modifier: Modifier = Modifier,
  onPieceMoved: (offset: Offset) -> Unit
) {
  val offset = mutableStateOf(Offset.Zero)
  val dragObserver = object : DragObserver {
    override fun onStart(downPosition: Offset) {
      offset.value = downPosition
    }

    override fun onDrag(dragDistance: Offset): Offset {
      offset.value = offset.value + dragDistance
      return dragDistance
    }

    override fun onStop(velocity: Offset) {
      onPieceMoved(offset.value)
      offset.value = Offset.Zero
    }
  }
  Image(
    vectorXmlResource(type.path),
    modifier = modifier
      .fillMaxSize(0.7F)
      .align(Alignment.Center)
      .dragGestureFilter(dragObserver, canDrag = { true }, startDragImmediately = true)
      .offset {
        val value = offset.value
        IntOffset(value.x.roundToInt(), value.y.roundToInt())
      }
  )
}

