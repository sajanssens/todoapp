package nl.bramjanssens.todoapp.manageitems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.bramjanssens.todoapp.ui.theme.TodoAppTheme
import java.util.UUID

@Composable
fun TodoItemRow(
    todo: TodoItem,
    modifier: Modifier = Modifier,
    onEdit: (UUID?) -> Unit,
    completeTodoItem: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checked = remember { mutableStateOf(todo.completed) }
        Checkbox(checked = checked.value, onCheckedChange = {
            completeTodoItem()
            checked.value = it
        })
        Text(text = todo.title)
        Spacer(modifier = Modifier.weight(1f)) // Pushes the icon to the far right
        Icon(
            Icons.Rounded.Edit,
            contentDescription = "Edit",
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { onEdit(todo.id) }
        )
    }
}

@Composable
fun TodoItemList(todos: List<TodoItem>, modifier: Modifier = Modifier, onEdit: (UUID?) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // remember the state; rememberSaveable keeps it even after the whole activity was destroyed and reloaded
    ) {
        todos.forEach { // will all get rendered eagerly, even when not necessary (yet)
            TodoItemRow(
                it,
                onEdit = onEdit,
                completeTodoItem = { it.toggle() }
            )
        }
    }
}

@Composable
fun TodoItemLazyList(
    todos: List<TodoItem>, modifier: Modifier = Modifier, onEdit: (UUID?) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(todos) { item ->  // will only get rendered when necessary, i.e. lazy
            TodoItemRow(
                item,
                onEdit = onEdit,
                completeTodoItem = { item.toggle() }
            )
        }
    }
}

@Composable
fun TodoItemInput(value: String = "", modifier: Modifier = Modifier, onOk: (String) -> Unit) {
    val textValue = remember { mutableStateOf(value) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = textValue.value,
            onValueChange = {
                textValue.value = it

            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    onOk(textValue.value)
                    textValue.value = ""
                }
            ),
            label = {
                Text(text = "Add item")
            },
            placeholder = {
                Text(text = "title...")
            },
        )
    }
}


@Composable
fun TodoMain(modifier: Modifier = Modifier) {
    var navController = rememberNavController()
    val todos = remember { mutableStateListOf(*todos().toTypedArray()) }

    // NavHost actually renders the composables inside when route is active.
    // Sort of a switch statement: if route == list, then show that composable, else ...

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TodoItemInput {
                    todos.add(TodoItem(it))
                }
                TodoItemLazyList(
                    todos = todos,
                    onEdit = { id -> navController.navigate("edit/$id") }
                )
            }
        }
        composable("edit/{id}") { stackEntry ->
            val id = stackEntry.arguments?.getString("id") ?: "0"
            val find = todos.find { item -> item.id == UUID.fromString(id) }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Editing task")
                TodoItemInput(value = find?.title ?: "not found") {
                    find?.title = it
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    TodoAppTheme {
        TodoMain()
    }
}

fun todos(): List<TodoItem> = List(15) { TodoItem("Item ${it + 1}") }