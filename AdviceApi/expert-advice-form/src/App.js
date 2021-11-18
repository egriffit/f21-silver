import './App.css';
import React from 'react'
import LoginForm from './Login/LoginForm';
import AdviceForm from './AdviceForm/AdviceForm';
import CreateSourceForm from './CreateSource/CreateSourceForm';
import CreateUserForm from './CreateUser/CreateUserForm';
import Logout from './Logout/Logout';
import Home from './Home/Home';
import Navigation from './Navigation/Navigation'
import PrivateRoute from './utilities/PrivateRoute'
import PublicRoute from './utilities/PublicRoute'

import { Switch } from 'react-router-dom';

function App() {
  return(
    <div className="App">
      <Switch>                
        <PublicRoute  path='/Login' component={LoginForm}/> 
        <PrivateRoute path='/AdviceForm' component={AdviceForm}/>                
        <PrivateRoute path='/SourceForm' component={CreateSourceForm}/> 
        <PrivateRoute exact path='/UserForm' component={CreateUserForm}/>  
        <PrivateRoute exact path='/Logout' component={Logout}/>
        <PrivateRoute exact path='/Home' component={Home}/>
     </Switch>
    <Navigation />
    </div>
  );
}

export default App;
