import React, { useState } from 'react';
import TodoForm from './TodoForm';
import Todo from './Todo';
import { Box,Typography } from '@mui/material';
import { getTodos, addTodo as addTodoFromApi ,deleteTodo, updateTodo as updateTodoFromApi,todoCompleted,todoUnCompleted} from '../data/api'

function TodoList() {
  const [todos, setTodos] = useState([]);
  useState(() => {
    getTodos()
      .then((res) => res.json())
      .then((result) => {
        console.log(result);
        setTodos(result);
      }
      ).catch((err) => {
        console.log(err);
      })
  }, []);

  const addTodo = todo => {
    if (!todo.text || /^\s*$/.test(todo.text)) {
      return;
    }
    addTodoFromApi(todo.text)
      .then(res => res.json())
      .then(result => {
        setTodos(todos.concat(result));
      }
      ).catch(err => {
        console.log(err);
      })
  };
  const updateTodo = (todoId, newValue) => {
    if (!newValue.text || /^\s*$/.test(newValue.text)) {
      return;
    }
    updateTodoFromApi(newValue.text,todoId)
      .then(res => res.json())
      .then(result => {
        setTodos(prev => prev.map(item => (item.id ==todoId ? result : item)));
      }
      ).catch(err => {
        console.log(err);
      })

    
  };

  const removeTodo = id => {
    const removedArr = [...todos].filter(todo => todo.id !== id);
    deleteTodo(id)
    .then(res => res.json())
    .then(result => {
      setTodos(todos.concat(result));
    }
    ).catch(err => {
      console.log(err);
    })
    setTodos(removedArr);
  };

  const complete=(id,isCompleted)=>{
    if(isCompleted){
      todoUnCompleted(id)
      .then(res => {
        console.log(res);
        setTodos(todos.map(todo => {
          if (todo.id ==id) {
            todo.completed = false;
          }
          return todo;
        }))
      }
      ).catch(err => {
        console.log(err);
      })
    }
    else{
      todoCompleted(id)
      .then(res => {
        console.log(res);
        setTodos(todos.map(todo => {
          if (todo.id ==id) {
            todo.completed = true;
          }
          return todo;
        }))
      }
      ).catch(err => {
        console.log(err);
      })
    }
    
    console.log("edit.id"+id);
  }
  


  return (
    <>
      <Box sx={{
        display: 'flex',
        flexDirection: 'column',
        marginBottom:10
      }}
      >
        <Typography component="h1" variant="h4" sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }} >
          What's the Plan for Today?
        </Typography>
        <TodoForm onSubmit={addTodo} />
        <Todo
          todos={todos}
          completeTodo={complete}
          removeTodo={removeTodo}
          updateTodo={updateTodo}
        />
      </Box>
    </>
  );
}

export default TodoList;