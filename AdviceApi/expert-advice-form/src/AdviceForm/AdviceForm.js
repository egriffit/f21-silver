import React, {useState, useReducer, useEffect} from 'react'
import '../App.css'
import  CreateSourceForm from '../CreateSource/CreateSourceForm'
import api from '../environment.js'
const axios = require('axios').default;


function AdviceForm(){
    const formReducer = (state, event) => {
        return {
          ...state,
          [event.name]: event.value
        }
    }
    const [sources, setSource] = useState([])
    const [formData, setFormData] = useReducer(formReducer, {});
    const [message, setMessage] = useState("");
    const [submitting, setSubmitting] = useState(false);
    const [response, setResponse] = useState([]);
    const [showSourceForm, setShowSourceForm] = useState(false);

    const getSources = async() => {
        await axios.get(api+'/sources')
        .then(response => {
            setSource(response.data)
        })
    }

    useEffect(()=>{
        getSources();
    })
    const cancelForm = () => { 
        document.getElementById("adviceForm").reset();
      }
      
    const handleChange = event=>{
        setFormData({
            name: event.target.name,
            value: event.target.value,
          });
    }
    const showForm = (event) => {
        event.preventDefault();

        if(showForm === true){
            setShowSourceForm(false)
        }
        if(showForm === false){
            setShowSourceForm(true)
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if((formData.adviceType != null) ||(formData.advice != null) || (formData.source != null)){
            setSubmitting(true)
            axios.post(api + '/addAdvice', {
                adviceType: formData.adviceType, 
                advice: formData.advice,
                sourceId: formData.source,
                })
            .then(response => {
              setResponse(response.data)
              setMessage(response.data.response)
              cancelForm()
              setSubmitting(false);
              //setUserSession(response.data.success, formData.username.value);
              //setSubmitting(false)
            })
        }else
        {
            setMessage("The advice type, advice and the source must be filled out to submit.")
        }
    }

    return(
        <div>
            {/* {formData && (
                <ul>
                    <li>AdviceType: {formData.adviceType}</li>
                    <li>Advice: {formData.advice}</li>
                    <li>Source: {formData.sourceId}</li>
                    <li>Api: {api}</li>

                </ul>
            )} */}
            {sources &&
            <datalist id='sources'>
                {/* loop over the books */}
                {sources.map((item, index) => (
                        <option key={index} value={item._id}>{item.title}</option>
                ))}
            </datalist>
            }
            <form id='adviceForm' onSubmit={handleSubmit}>
                <fieldset>
                <label>
                    <p>Advice Type:</p>
                </label>
                <select name='adviceType' 
                onChange={handleChange}>
                     <option key='1'></option>
                    <option key='2'>Build Muscle</option>
                    <option key='3'>Lose Weight</option>
                    <option key='4'>Gain Strength</option>
                    <option key='5'>Safety</option>
                </select><br />
                <label>
                    <p>Advice:</p>
                </label>
                <textarea name='advice' onChange={handleChange}/><br />
                <label>
                    <p>Source:</p>
                </label>
                <input name='source' list="sources" onChange={handleChange}/>
                <button type='button' name='addSource' 
                onClick={()=>{ 
                    if(showSourceForm){
                        setShowSourceForm(false)
                    }else if(!showSourceForm){
                        setShowSourceForm(true)
                    }
                }}>Add Source</button>
                </fieldset><br />


                <button type='submit'>Add Advice</button>
            </form><br />
            <p>{message}</p>
            {showSourceForm === true &&
                <CreateSourceForm shouldHide={showSourceForm} />
            }

        </div>
    )
}

export default AdviceForm;
