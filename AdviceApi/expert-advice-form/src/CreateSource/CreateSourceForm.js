import React, {useReducer, useState, useEffect} from 'react'
import '../App.css'
import './CreateSourceForm.css'
import api from '../environment.js'
const axios = require('axios').default;

function CreateSourceForm(props){
    const formReducer = (state, event) => {
        return {
          ...state,
          [event.name]: event.value
        }
    }
    const [formData, setFormData] = useReducer(formReducer, {});
    const [authorCount, setAuthorCount] = useState(4);
    const [editorCount, setEditorCount] = useState(4);
    const [submitting, setSubmitting] = useState(false);
    const [response, setResponse] = useState([]);
    const [message, setMessage] = useState("");

    const handleSubmit = event=>{
        event.preventDefault()
            setSubmitting(true)
            //handle authors
            axios.post(api + '/addSource',
            {
                    sourceType: formData.sourceType,
                    title: formData.title,
                    authors: [
                        formData.author_1,
                        formData.author_2
                    ],
                    year: formData.year,
                    journal: formData.journal,
                    volume: formData.volume,
                    issue: formData.issue,
                    editors: [],
                    bookTitle: formData.bookTitle,
                    publisher: formData.publisher,
                    publisherLocation: formData.publisher,
                    webpage: formData.webpage
            })
            .then(response => {
              setSubmitting(false);
              setResponse(response.data)
              if(response.data.success)
              {
                setMessage(response.data.response)
              }
              else
              {
                setMessage(response.data.message)
              }
            }) 
    }

    const validateArticleFields = ()=>{
        console.log()
    }
    
    const validateBookFields = ()=>{
        console.log()

    }
    const validateWebpageFields = ()=>{
        console.log()
    }
   
    const handleChange = event=>{
        if(event.target.value === ""){
            event.target.value = " ";
        }
        setFormData({
            name: event.target.name,
            value: event.target.value,
          });
    }

    return(
        <>
        <div className={props.shouldHide ? undefined : 'hidden'}>
            <br />
            <h1>Create Source Form</h1>
            
             <form id='sourceForm' onSubmit={handleSubmit}>
                <fieldset>
                <label>
                    <p>Source Type:</p>
                </label>
                <select name='sourceType' 
                onChange={handleChange}>
                     <option key='1'></option>
                    <option key='2'>Article</option>
                    <option key='3'>Book</option>
                    <option key='4'>Webpage</option>
                </select><br />
                <label>
                    <p>Title:</p>
                </label>
                <input name='title' onChange={handleChange}/><br/>
                <table>
                    <tr>
                {[...Array(authorCount)].map((x, index) =>
                    <td>
                    <label>
                        <p>Author {index+1}  </p>
                    </label>
                    <input key={index} name={"author_"+(index+1)} onChange={handleChange}/><br/>
                    </td>
                )};
                </tr>
                </table>
                <label>
                    <p>Year:</p>
                </label>
                <input name='year' onChange={handleChange}/>
                <label>
                    <p>Journal:</p>
                </label>
                <input name='journal' onChange={handleChange}/><br/>
                <label>
                    <p>Volume:</p>
                </label>
                <input name='volume' onChange={handleChange}/><br/>
                <label>
                    <p>Issue:</p>
                </label>
                <input name='issue' onChange={handleChange}/><br/>
                <label>
                    <p>Book Title:</p>
                </label>
                <input name='bookTitle' onChange={handleChange}/><br/>
                <table>
                    <tr>
                {[...Array(editorCount)].map((x, index) =>
                    <td>
                    <label>
                        <p>Editor {index+1}  </p>
                    </label>
                    <input key={index} name={"editor_"+(index+1)} onChange={handleChange}/><br/>
                    </td>
                )};
                </tr>
                </table>
                <label>
                    <p>Publisher:</p>
                </label>
                <input name='publisher' onChange={handleChange}/><br/>
                <label>
                    <p>Publisher Location:</p>
                </label>
                <input name='publisherLocation' onChange={handleChange}/><br/>
                <label>
                    <p>Webpage:</p>
                </label>
                <input name='webpage' onChange={handleChange}/><br/>
                </fieldset>
                <button type='submit'>Add Advice</button>
            </form>
            <p>
                {message}
            </p>
            </div>
        </>
    )
}

export default CreateSourceForm;
