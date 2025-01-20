package nl.bramjanssens.todoapp.manageitems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.bramjanssens.todoapp.ui.theme.TodoAppTheme

@Composable
fun Show(modifier: Modifier = Modifier, item: TodoItem, onComplete: () -> Unit) {
    Box(modifier.clickable(onClick = onComplete) ) {
        Row {
            Text(text = item.title + " | ")
            Text(text = item.completed.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    TodoAppTheme {
        Show(item = TodoItem("Training volgen", false)) {
        }
    }
}