// export const getUser = () => {
//   const userStr = sessionStorage.getItem('username');
//   if (userStr) return JSON.parse(userStr);
//   else return null;
// }
 
// return the token from the session storage
export const getSuccess = () => {
  return sessionStorage.getItem('success') || null;
}
 
// remove the token and user from the session storage
export const removeUserSession = () => {
  sessionStorage.removeItem('success');
}
 
// set the token and user from the session storage
export const setUserSession = (success) => {
  sessionStorage.setItem('success', success);
}

export const getSessionStorageOrDefault = (key, defaultValue) => {
  const stored = sessionStorage.getItem(key);
  return (!stored) ? defaultValue : JSON.parse(stored);
}