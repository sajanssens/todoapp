package nl.bramjanssens.todoapp.manageitems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.bramjanssens.todoapp.ui.theme.TodoAppTheme

@Composable
fun TodoItemRow(todo: TodoItem, modifier: Modifier = Modifier, completeTodoItem: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checked = remember { mutableStateOf(todo.completed) }
        Checkbox(checked = checked.value, onCheckedChange = {
            completeTodoItem()
            checked.value = it
        })
        Text(text = todo.title)
    }
}

@Composable
fun TodoItemList(todos: List<TodoItem>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // remember the state; rememberSaveable keeps it even after the whole activity was destroyed and reloaded
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

@Composable
fun TodoItemInput(modifier: Modifier = Modifier, onOk: (String) -> Unit) {
    val textValue = remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textValue.value,
            onValueChange = {
                textValue.value = it
            },
            label = {
                Text(text = "Add item")
            },
            placeholder = {
                Text(text = "title...")
            },
        )
        Button(
            modifier = modifier,
            onClick = {
                onOk(textValue.value)
                textValue.value = ""
            }
        ) {
            Text("Ok")
        }
    }
}


@Composable
fun TodoMain(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val todos = remember { mutableStateListOf(*todos().toTypedArray()) }
        TodoItemInput { todos.add(TodoItem(it)) }
        TodoItemLazyList(todos)
    }
}

@Composable
fun ToggleableCheckbox() {
    // Remember the state of the checkbox
    val isChecked = remember { mutableStateOf(false) }

    // Checkbox that toggles its state when clicked
    Checkbox(
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it }
    )
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    TodoAppTheme {
        TodoMain()
    }
}

fun todos(): List<TodoItem> = List(15) { TodoItem("Item ${it + 1}") }