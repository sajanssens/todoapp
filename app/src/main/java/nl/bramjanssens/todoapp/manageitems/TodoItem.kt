package nl.bramjanssens.todoapp.manageitems

import java.util.UUID

data class TodoItem(
    var title: String,
    var completed: Boolean = false,
    var id: UUID? = UUID.randomUUID()
) {
    fun toggle() {
        this.completed = !this.completed
    }
}