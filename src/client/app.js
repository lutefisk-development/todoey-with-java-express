


(function($) {

  $(document).ready(function() {

    let todos = [];

    const getTodos = async () => {
      todos = [];
      // get todos
      let result = await fetch('/api/todos');
      todos = await result.json();

      todos.forEach(item => {
        if(item.completed) {
          $("#todo__list").append($('<div class="wrapper"><li class="list-group-item complete"><i class="fas fa-check fa-lg"></i>'+ item.todo +'</li><i class="fas fa-minus-square fa-2x"></i></div>'));
        }
        if(!item.completed) {
          $("#todo__list").append($('<div class="wrapper"><li class="list-group-item"><i class="fas fa-check fa-lg"></i>'+ item.todo +'</li><i class="fas fa-minus-square fa-2x"></i></div>'));
        }
      });
    }

    const createTodo = async todo => {
      $("#todo__list").empty();

      let result = await fetch('/api/todos', {
        method: "POST",
        body: JSON.stringify(todo),
      });

      console.log(result);
      getTodos();
    };

    const updateTodo = async todo => {
      $("#todo__list").empty();

      let result = await fetch(`/api/todos/${todo.id}`, {
        method: "PUT",
        body: JSON.stringify({completed: !todo.completed}),
      });

      console.log(result);
      getTodos();
    };

    const deleteTodo = async todo => {
      $("#todo__list").empty();

      let result = await fetch(`/api/todos/${todo.id}`, {
        method: "DELETE",
      });

      console.log(result);
      getTodos();
    };

    $("#todo__add-todo").click(function() {
      $("#todo__input").val("");
      createTodo({
        todo: $("#todo__input").val(),
        completed: false
      });
    });

    $(document).on('click', 'li.list-group-item', function() {
      todos.forEach(todo => {
        if($(this).text() === todo.todo) {
          updateTodo(todo);
        }
      });
    });

    $(document).on('click', 'i.fa-minus-square', function() {
      todos.forEach(todo => {
        if($(this).parent().text() === todo.todo) {
          deleteTodo(todo);
        }
      });
    });

    // getting all todos from server
    getTodos();

  });

})(jQuery);