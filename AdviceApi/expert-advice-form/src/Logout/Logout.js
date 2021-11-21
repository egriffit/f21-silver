import React, {useState, useEffect} from 'react'
import '../App.css'
import * as utilities from '../utilities/common.js' 
import {
    Link,
    useHistory
  } from "react-router-dom";

function Logout(){
    const history = useHistory()
    const [loggedIn, setLoggedIn] = useState(false);

    utilities.removeUserSession()
    
      useEffect(()=>{
        setLoggedIn(utilities.getSuccess())
    }, [loggedIn]);

    if (!loggedIn) {
       history.push('/Login')
    }


      return(
          <div>
            { loggedIn && 
                <div>
                    <h1>Logging Out...</h1>
                </div>
            }
            { loggedIn === false && 
                <>
                <h1>Successfully Logged out</h1>
                <Link to='/logout' >Log in</Link>
                </>
            }
      );
          </div>
      )
}

export default Logout