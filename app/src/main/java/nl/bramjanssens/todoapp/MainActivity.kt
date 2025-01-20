package nl.bramjanssens.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.bramjanssens.todoapp.manageitems.TodoItemLazyList
import nl.bramjanssens.todoapp.manageitems.todos
import nl.bramjanssens.todoapp.ui.theme.TodoAppTheme

class MainActivity :
    ComponentActivity() { // een activity is een startpunt, deze is in de manifest als main aangeduid wanneer de app vanuit de launcher wordt gestart
    // bridge tussen buitenwereld en compose:
    // dit is de root composition
    // zijn parent is de zgn. Recomposer
    // recompose gebeurt automatisch elke keer als er data verandert
    override fun onCreate(savedInstanceState: Bundle?) { // verplicht
        super.onCreate(savedInstanceState) // verplicht
        setContent { // verplicht
            // hier: welke composables (of oude onderdelen) worden getoond
            TodoAppTheme { // modifier verandert styling, behaviour, ...; is een decorator; kun je chainen en worden doorgegeven naar de children
                //            bijv. als je op toplevel dark mode meegeeft, wordt alles dark mode
//                Greeting(
//                    name = "Bram"
//                )
//                val i = TodoItem("Training volgen")
//                ItemRow(item = i, onComplete = {
//                    i.toggle()
//                })

//                DemoDetectDragGestures()
//                TodoItemsList(todos())
                TodoItemLazyList(todos())
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoAppTheme {
        Greeting("Android")
    }
}