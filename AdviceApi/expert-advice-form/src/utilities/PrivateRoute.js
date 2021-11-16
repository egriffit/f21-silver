 //taken from https://www.cluemediator.com/login-app-create-login-form-in-reactjs-using-secure-rest-api

import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { getSuccess } from './common.js';
 
// handle the private routes
function PrivateRoute({ component: Component, ...rest }) {
  return (
    <Route
      {...rest}
      render={(props) => getSuccess() ? <Component {...props} /> : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />}
    />
  )
}
 
export default PrivateRoute;