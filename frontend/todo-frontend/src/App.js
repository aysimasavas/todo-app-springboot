
import './App.css';
import Login from './components/Login'
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import TodoList from './components/TodoList';


function App() {
  return (
    <div>
    <Router>
      <div>
        <Routes>
        <Route path="/" exact element={<Login></Login>} />
        <Route path="/home" element={<TodoList></TodoList>} />
        </Routes>
      </div>
    </Router>
    </div>
  );
}

export default App;
