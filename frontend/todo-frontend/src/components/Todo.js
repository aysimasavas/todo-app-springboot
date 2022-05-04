import React, { useState } from 'react';
import TodoForm from './TodoForm';
import { RiCloseCircleLine } from 'react-icons/ri';
import { TiEdit } from 'react-icons/ti';
import { Checkbox } from '@material-ui/core';
import {todoCompleted,todoUnCompleted} from '../data/api'

const Todo = ({ todos, removeTodo, updateTodo,completeTodo }) => {
  
  const [edit, setEdit] = useState({
    id: null,
    value: ''
  });
 

  const submitUpdate = value => {
    updateTodo(edit.id, value);
    setEdit({
      id: null,
      value: ''
    });
  };

  if (edit.id) {
    return <TodoForm edit={edit} onSubmit={submitUpdate} />;
  }

  return todos.map((todo, index) => (
    <div
      className={todo.isComplete ? 'todo-row complete' : 'todo-row'}
      key={index}
    >
      <div key={todo.id} onClick={() => completeTodo(todo.id,todo.completed)}>
        <Checkbox  checked={todo.completed} />
        {todo.content}

      </div>
      <div className='icons'>
        <RiCloseCircleLine
          onClick={() => removeTodo(todo.id)}
          className='delete-icon'
        />
        <TiEdit
          onClick={() => setEdit({ id: todo.id, value: todo.content })}
          className='edit-icon'
        />
      </div>
    </div>
  ));
};

export default Todo;