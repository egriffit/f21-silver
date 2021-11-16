import React, {useState, useReducer} from 'react'
import '../App.css'
import * as utilities from '../utilities/common.js' 
import { useHistory } from 'react-router-dom';

const axios = require('axios').default;

function LoginForm(){
    const formReducer = (state, event) => {
        return {
          ...state,
          [event.name]: event.value
        }
    }
    const history = useHistory()
    const [submitting, setSubmitting] = useState(false);
    const [formData, setFormData] = useReducer(formReducer, {});
    const [response, setResponse] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isLoggedIn, setLoggedIn] = useState(false);
    
    const handleChange = event=>{
        setFormData({
            name: event.target.name,
            value: event.target.value,
          });
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        setSubmitting(true)
            setError(null);
            setLoading(true);
            axios.post('http://localhost:7000/adviceapi/login', { username: formData.username, password: formData.password })
            .then(response => {
              setLoading(false);
              setResponse(response.data)
              setLoggedIn(utilities.setUserSession(response.data.success))
              if(response.data.success)
              {
                history.push('/Home')
              }
              //setUserSession(response.data.success, formData.username.value);
              //setSubmitting(false)
            })
    }

      
    return(
        <div>
            <br />
            <br />
            <br />
            <br />
            <h1> Login Form</h1>
            {submitting &&
                <div>Logging in...
                    You are submitting 
                    <ul>
                    {Object.entries(formData).map(([name, value]) => (
                        <li key={name}><strong>{name}</strong>:{value.toString()}</li>
                     ))}
                     
                </ul>
                Response
                <ul>
                <li> Username: {formData.username} </li>
                {Object.entries(response).map(([name, value]) => (
                    <li key={name}><strong>{name}</strong>:{value.toString()}</li>
                 ))}
                </ul>
                </div>
            }
            {
               
            }
            <form onSubmit={handleSubmit}>
                <fieldset>
                <label>
                    <p>Username:</p>
                </label>
                <input name='username' onChange={handleChange}/><br/>
                <label>
                    <p>Password:</p>
                </label>
                <input type='password' name='password' onChange={handleChange}/>

                </fieldset>
                <button type='submit' onChange={setFormData}>Log in</button>
            </form>
        </div>
    )
}

export default LoginForm;