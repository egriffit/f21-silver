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
    const [message2, setMessage2] = useState("");
    const validate = (formState, type) =>{
        if(type === "number"){
            return (formState === "" || formState === undefined || formState === null) ? 0: formState
        }else{ //it's a string
            return (formState === "" || formState === undefined || formState === null) ? " ": formState
        }
    }

    const handleSubmit = async  (event)=>{
        event.preventDefault()
        //handle authors
        if((formData.sourceType != undefined || formData.sourceType !== null || formData.sourceType !== "")
            && ((formData.title != undefined && formData.title != null && formData.title !== "")
                || (formData.bookTitle != undefined || formData.bookTitle != null && formData.bookTitle != "")))
        {

            // formData.author_1 = (formData.author_1 == "" || formData.author_1 == undefined || formData.author_1 == null)? " " : formData.author_1;
            // formData.author_2 = (formData.author_2 == "" || formData.author_2 == undefined || formData.author_2 == null)? " " : formData.author_2
            // formData.journal = (formData.journal == "" || formData.journal == undefined || formData.author_1 === null)? " " : formData.journal;
            // formData.volume = (formData.volume == "" || formData.volume == undefined || formData.volume == null)? " " : formData.volume;
            // formData.issue = (formData.issue == "" || formData.issue === undefined || formData.issue === null) ? " " : formData.issue;
            // formData.bookTitle = (formData.bookTitle == "" || formData.bookTitle === undefined || formData.bookTitle == null)? " " : formData.bookTitle;
            // formData.title = (formData.title == "" || formData.title === undefined || formData.title === null) ? " " : formData.title;
            // formData.publisher = (formData.publisher == "" || formData.publisher === undefined || formData.publisher === null) ? " " : formData.publisher;
            // formData.publisherLocation = (formData.publisherLocation == "" || formData.publisherLocation === undefined || formData.publisherLocation === null) ? " " : formData.publisherLocation;
            // formData.webpage = (formData.webpage == "" || formData.webpage === undefined || formData.Webpage === null) ? " " : formData.webapge;
            try{
                setSubmitting(true)
                var AuthorsArray = []
                if(validate(formData.author_1, "string")!= " "){
                    AuthorsArray.push(formData.author_1);
                }
                if(validate(formData.author_2, "string")!= " "){
                    AuthorsArray.push(formData.author_2);
                }
                if(validate(formData.author_3, "string")!= " "){
                    AuthorsArray.push(formData.author_3);
                }
                if(validate(formData.author_4, "string")!= " "){
                    AuthorsArray.push(formData.author_4);
                }
                var EditorsArray = []
                if(validate(formData.editor_1, "string")!= " "){
                    EditorsArray.push(formData.editor_1);
                }
                if(validate(formData.editor_2, "string")!= " "){
                    EditorsArray.push(formData.editor_2);
                }
                if(validate(formData.editor_3, "string")!= " "){
                    EditorsArray.push(formData.editor_3);
                }
                if(validate(formData.editor_4, "string")!= " "){
                    EditorsArray.push(formData.editor_4);
                }

                await axios.post(api + '/addSource',
                {
                            sourceType: validate(formData.sourceType, "string"),
                            title: validate(formData.title, "string"),
                            authors: AuthorsArray,
                            year: validate(formData.year, "number"),
                            journal: validate(formData.journal, "string"),
                            volume: validate(formData.volume, "string"),
                            issue: validate(formData.issue, "number"),
                            editors: EditorsArray,
                            bookTitle: validate(formData.bookTitle, "string"),
                            publisher: validate(formData.publisher, "string"),
                            publisherLocation: validate(formData.publisherLocation, ""),
                            webpage: validate(formData.webpage, "")
                    }).then((response)=>{
                    setMessage2(response.data.response);
                });
                setSubmitting(false)
            }catch(error){
                setMessage2("Error: " + error);
            }
        }else{
            setMessage2("A title and source type must be filled");
        }
    }

    const handleChange = event=>{
        setFormData({
            name: event.target.name,
            value: event.target.value,
          });
    }

    return(
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
            {submitting && (
                <p>Submitting...</p>
            )}
            <p>
                {message2}
            </p>
            </div>
    )
}

export default CreateSourceForm;