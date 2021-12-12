import React from 'react'
import {Container, Navbar, Nav} from 'react-bootstrap'
import logo from '../WGER.png'
function Navigation(){
    return(
                <>
                    <link
                        rel="stylesheet"
                        href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                        crossorigin="anonymous"
                    />
                    <Navbar collapseOnSelect fixed='top' expand='sm' bg='dark' variant='dark'>
                        <Container>
                        <Navbar.Brand>
                                <img
                                  alt="logo"
                                  src={logo}
                                  width="45"
                                  height="30"
                                />
                            </Navbar.Brand>
                            <Navbar.Toggle aria-controls='responsive-navbar-nav' />
                            <Navbar.Collapse id='responsive-navbar-nav'>
                                <Nav>
                                    <Nav.Link href='/Home'>Create Advice</Nav.Link>
                                    <Nav.Link href='/UserForm'>Create User</Nav.Link>
                                    <Nav.Link href='/Logout'>Logout Page</Nav.Link>
                                </Nav>
                            </Navbar.Collapse>
                        </Container>
                    </Navbar>
                </>   
    )
}

export default Navigation;