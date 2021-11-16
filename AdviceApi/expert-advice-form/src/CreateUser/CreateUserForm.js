import React, {useState, useReducer} from 'react'
import '../App.css'

const axios = require('axios').default;

function CreateUserForm(){
    const formReducer = (state, event) => {
        return {
          ...state,
          [event.name]: event.value
        }
    }

    const [formData, setFormData] = useReducer(formReducer, {});
    const [message, setMessage] = useState("");
    const [submitting, setSubmitting] = useState(false);
    const [response, setResponse] = useState([]);

    const handleChange = event=>{
        setFormData({
            name: event.target.name,
            value: event.target.value,
          });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if(formData.password === formData.password2)
        {
            setSubmitting(true)
            axios.post('http://localhost:7000/adviceapi/createUser', { username: formData.username, password: formData.password })
            .then(response => {
              setSubmitting(false);
              setResponse(response.data)
              if(response.data.success)
              {
                setMessage(formData.username + "was succcessfully created.")
              }
              //setUserSession(response.data.success, formData.username.value);
              //setSubmitting(false)
            })
        }else
        {
            setMessage("The passwords provided do no match. Plese try again.")
        }
    }
    return(
        <div>
            <br />
            <br />
            <br />
            <br />
            <h1>Create User Form</h1>
            <form onSubmit={handleSubmit}>
            <fieldset>
                <label>
                    <p>Username:</p>
                </label>
                <input name='username' onChange={handleChange}/><br/>
                <label>
                    <p>Password:</p>
                </label>
                <input name='password' onChange={handleChange}/><br/>

                <label>
                    <p>Verify Password:</p>
                </label>
                <input name='password2' onChange={handleChange}/>
            </fieldset>
                <button type='submit' onChange={setFormData}>Log in</button>
            </form>
            <p>{message}</p>
        </div>
    )
}

export default CreateUserForm;