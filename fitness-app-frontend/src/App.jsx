import { Button } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { BrowserRouter as Router, Navigate, Route, Routes, useLocation } from "react-router";
import { setCredentials } from "./store/authSlice";
import Box from '@mui/material/Box';
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetail from "./components/ActivityDetail";

const ActivitiesPage = () => {
  return (
  <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>
      <ActivityForm onActivitiesAdded = { () => window.location.reload()}/>
      <ActivityList />
    </Box>
  );
}

function App() {

  const { token, tokenData, logIn, logOut, isAuthenticated } = useContext(AuthContext);
  const dispatch = useDispatch();

  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>

      {!token ? (
        <Button
          variant="contained"
          onClick={() => {
            logIn()
          }}
          sx={{
            backgroundColor: "#1976d2",
            "&:hover": {
              backgroundColor: "#115293",
            },
            padding: "15px",
            margin: "250px",
            marginLeft: "690px",
            borderRadius: "10px"

          }

          }
        >
          LOGIN
        </Button>
      ) : (

        

        <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>
          <Button variant="contained" color="secondary" onClick={logOut}>
                  Logout
                </Button>
          <Routes>
            <Route path="/activities" element={<ActivitiesPage />}/>
            <Route path="/activities/:id" element={<ActivityDetail />}/>
            <Route path="/" element={token ? <Navigate to="/activities" replace/> : <div>Welcome! Please login</div>}/>
          </Routes>
        </Box>
      )}

    </Router>
  )
}

export default App
