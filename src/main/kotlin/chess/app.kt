package chess

import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import kotlin.math.roundToInt

val game = Game()
val itemSize = 120.dp

fun main() {
  Window(title = "Compose for Desktop", size = IntSize(1200, 1200)) {
    MaterialTheme {
      Column {
        Button(onClick = {
          game.restartGame(whiteOnBottom = true)
        }) {
          Text("Restart, Play white")
        }
        Button(onClick = {
          game.restartGame(whiteOnBottom = false)
        }) {
          Text("Restart, Play black")
        }
        Box {
          Column {
            game.board.value.forEachIndexed { rowIndex, column ->
              val evenRow = rowIndex % 2 == 0
              Row {
                column.forEachIndexed { columnIndex, chessPiece ->
                  val coordinate = Coordinate(row = rowIndex, column = columnIndex)
                  val evenColumn = columnIndex % 2 == 0
                  val blackField = (evenRow && !evenColumn) || (evenColumn && !evenRow)
                  val color = if (blackField) Color(0xFF616161) else Color(0xffbdbdbd)
                  val strokeColor = if (coordinate in game.validMoves.value) Color(0xff66bb6a) else Color.Transparent
                  Box(Modifier.width(itemSize)
                    .background(color = color)
                    .height(itemSize)
                    .border(width = 6.dp, color = strokeColor))
                }
              }
            }
          }
          game.board.value.forEachIndexed { rowIndex, column ->
            val evenRow = rowIndex % 2 == 0
            column.forEachIndexed { columnIndex, chessPiece ->
              val coordinate = Coordinate(row = rowIndex, column = columnIndex)
              if (chessPiece != null) {
                ChessPieceImage(type = chessPiece, coordinate, modifier = Modifier
                  .size(itemSize)
                  .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                      placeable.place(columnIndex * placeable.width, rowIndex * placeable.height)
                    }
                  })
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
fun ChessPieceImage(
  type: Piece,
  coordinate: Coordinate,
  modifier: Modifier = Modifier
) {
  val offset = mutableStateOf(Offset.Zero)
  val dragObserver = object : DragObserver {
    override fun onStart(downPosition: Offset) {
      offset.value = downPosition
      game.startMove(coordinate)
    }

    override fun onCancel() {
      offset.value = Offset.Zero
      game.stopMove()
    }

    override fun onDrag(dragDistance: Offset): Offset {
      offset.value = offset.value + dragDistance
      return dragDistance
    }

    override fun onStop(velocity: Offset) {
      game.stopMove()
      val moveX = (offset.value.x.dp / itemSize)
        .roundToInt()
      val moveY = (offset.value.y.dp / itemSize)
        .roundToInt()
      game.move(
        coordinate,
        Coordinate(row = coordinate.row + moveY, column = coordinate.column + moveX)
      )
      offset.value = Offset.Zero
    }
  }
  Image(
    vectorXmlResource(type.path),
    modifier = modifier
      .dragGestureFilter(dragObserver, canDrag = { true }, startDragImmediately = true)
      .offset {
        val value = offset.value
        IntOffset(value.x.roundToInt(), value.y.roundToInt()).also {
          println("itOffset=$it")
        }
      }
  )
}

