package nl.bramjanssens.todoapp.manageitems

data class TodoItem(val title: String, var completed: Boolean = false){
    fun toggle(){
        this. completed = !this.completed
    }
}