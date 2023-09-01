
import './App.css';
import SetClock from './Components/SetClock'
import SetAlarms from './Components/SetAlarms';
import { useEffect, useState } from 'react';

let nextId=1;
const initialAlarms = [
];

function App() {
  const [alarms, setAlarms] = useState(initialAlarms);
  const [JSONStr, setJSONStr] = useState([]);
  const [counter,setCounter] = useState(1);
  function handleAddAlarm(title, time) {
    const newalarm =  {
      //id: counter,
      id: 'id'+(new Date()).getTime(),
      title: title,
      time: time
    };
    setAlarms([
      ...alarms,
     newalarm
    ])
    
    const alarmBody = JSON.stringify(newalarm);
    showAndroidToast(alarmBody);
  }
  useEffect(()=>{
     try{
        let tempAlarms = JSON.parse(JSONStr);
        setCounter(tempAlarms.length);
     }
     catch(err){
       setCounter(0);
     }
  },[JSONStr])

  function handleDeleteAlarm(alarmId) {
    setJSONStr(
      JSONStr.filter(alarm => alarm.id !== alarmId)
    );
    if(window.Android){
      // const alarmData = JSONStr.filter(alarm => alarm.id !== alarmId);
      // alarmData.length ?  window.Android.deleteAlarm(JSON.stringify(alarmData),alarmId):clearAllAlarms();
      window.Android.cancelSetAlarm(alarmId);

    }
  }
  // function showAndroidToast(body) {
  //   // console.log(alarms);
  //     if(window.Android){
        
  //       window.Android.showToast(body);
  //     }
  // }
  function showAndroidToast(body) {
    // console.log(alarms);
      if(window.Android){
        
        window.Android.getAlarmFromWebview(body);
      }
  }
  function clearAllAlarms() {
    if(window.Android) {
      let alarmIds = JSONStr.map((alarm)=>alarm.id).join(',');
      //window.Android.clearAll(alarmIds);
      window.Android.clearAll();
    }
  }

  function dismissSetAlarm() {
    if(window.Android) {
      window.Android.dismissAlarm(JSONStr.id);
    }
  }

  window.receiveDataFromAndroid = function(res) {
    
    var str = atob(res);
    let JSONString = JSON.parse(str);
    //setAlarms(JSONString);
    setJSONStr(JSONString);
  }
  return (
    <>
      <button onClick={clearAllAlarms}>
        Clear all
      </button>
      <button onClick={dismissSetAlarm}>
        Dismiss Alarm
      </button>
      <SetAlarms 
        alarms={JSONStr}
        onDeleteAlarm={handleDeleteAlarm}
      />
      <SetClock onAddAlarm={handleAddAlarm}/>
    </>
  );
}

export default App;
