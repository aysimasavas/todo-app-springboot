const BASE_URL = "http://localhost:8070"
const login = (username, password) => {
    const request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            userName: username,
            password: password
        })
    }
    return fetch(BASE_URL + "/auth/login",request)
}
const register = (username, password) => {
    const request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            userName: username,
            password: password
        })
        
    }
    console.log(request)
    return fetch(BASE_URL + "/auth/register",request)

}

const getTodos = () => {
    if (!localStorage.getItem("token")) {
       return Promise.reject("No token found")
    }else{
        const request = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        return fetch(BASE_URL + "/todo/",request)
    }
}

const addTodo = (todo) => {
    
    var token = localStorage.getItem("token")
    console.log(token)
    console.log(todo)
    if (!localStorage.getItem("token")) {
         return Promise.reject("No token found")
    }else{
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            body: JSON.stringify({
                content: todo
            })
        }
        return fetch(BASE_URL + "/todo/add",request)
    }
}
const deleteTodo=(id)=>{
    var token = localStorage.getItem("token")
    console.log(token)
    console.log(id)
    if (!localStorage.getItem("token")) {
         return Promise.reject("No token found")
    }else{
        const request = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        return fetch(BASE_URL + "/todo/delete/"+id,request)
    }

}

const updateTodo=(todo,id)=>{
    var token = localStorage.getItem("token")
    console.log(token)
    console.log(todo)
    if (!localStorage.getItem("token")) {
         return Promise.reject("No token found")
    }else{
        const request = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            body: JSON.stringify({
                id:id,
                content: todo
            })
        }
        return fetch(BASE_URL + "/todo/update",request)
    }
}

const todoCompleted=(id)=>{
    var token = localStorage.getItem("token")

    if (!localStorage.getItem("token")) {
         return Promise.reject("No token found")
    }else{
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        return fetch(BASE_URL + "/todo/complete/"+id,request)
    }
}

const todoUnCompleted=(id)=>{
    
    var token = localStorage.getItem("token")

    if (!token) {
         return Promise.reject("No token found")
    }else{
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }
        return fetch(BASE_URL + "/todo/uncomplete/"+id,request)
    }
}
module.exports = { login, register , getTodos, addTodo,deleteTodo,updateTodo ,todoCompleted,todoUnCompleted}