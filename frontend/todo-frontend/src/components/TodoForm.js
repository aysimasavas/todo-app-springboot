import React, { useState, useEffect, useRef } from 'react';

function TodoForm(props) {
  const [input, setInput] = useState(props.edit ? props.edit.value : '');

  const inputRef = useRef(null);

  useEffect(() => {
    inputRef.current.focus();
  });

  const handleChange = e => {
    setInput(e.target.value);
  };

  const handleSubmit = e => {
    e.preventDefault();

    props.onSubmit({
      id: Math.floor(Math.random() * 10000),
      text: input
    });
    setInput('');
  };

  return (
    <form onSubmit={handleSubmit} className='todo-form'
      sx={{
        marginTop: 8,
        display: 'flex',
        flexDirection: 'column',
        marginBottom:10
      }}>
      {props.edit ? (
        <>
          <div style={{
            position: 'absolute', left: '50%', top: '40%',
            transform: 'translate(-50%, -50%)'
          }}>
            <input
              placeholder='Update your item'
              value={input}
              onChange={handleChange}
              name='text'
              ref={inputRef}
              className='todo-input edit'
            />
            <button onClick={handleSubmit} className='todo-button edit'>
              Update
            </button>
          </div>
        </>
      ) : (
        <>
          <div style={{
            position: 'absolute', left: '50%', top: '25%',
            transform: 'translate(-50%, -50%)'
          }}>
            <input
              placeholder='Add a todo'
              value={input}
              onChange={handleChange}
              name='text'
              className='todo-input'
              ref={inputRef}
            />
            <button onClick={handleSubmit} className='todo-button'>
              Add todo
            </button>
          </div>
        </>
      )}
    </form>
  );
}

export default TodoForm;