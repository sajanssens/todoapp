package nl.bramjanssens.todoapp.manageitems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.bramjanssens.todoapp.ui.theme.TodoAppTheme

@Composable
fun TodoItemRow(todo: TodoItem, modifier: Modifier = Modifier, onComplete: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(todo.completed, onCheckedChange = { onComplete() })
        Text(text = todo.title)
    }
}

@Composable
fun TodoItemList(todos: List<TodoItem>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        todos.forEach { // will all get rendered eagerly, even when not necessary (yet)
            TodoItemRow(it) { it.toggle() }
        }
    }
}

@Composable
fun TodoItemLazyList(todos: List<TodoItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
//            .verticalScroll(rememberScrollState()) // is al scrollable, haal dit weg, anders crash!
    ) {
        items(todos) { // will only get rendered when necessary, i.e. lazy
            TodoItemRow(it) { it.toggle() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    TodoAppTheme {
        TodoItemList(todos())
    }
}

fun todos(): List<TodoItem> = List(25) { TodoItem("Item ${it + 1}") }