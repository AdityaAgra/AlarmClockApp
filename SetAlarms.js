import { createRenderer } from "react-dom/test-utils";

export default function SetAlarms({
    alarms, onDeleteAlarm
}) {
    return (
        <div style={{textAlign:"center"}}>
            <ul style={{display: "inline-block",textAlign:"left"}}>
            {alarms.map(alarms => (
                <li key={alarms.id} style={{margin:"5px"}}>
                <label>
                    {alarms.title}
                </label>
                <label style={{marginLeft:"10px"}}>
                    {alarms.time}
                </label>
                <button style={{marginLeft:"10px"}} onClick={() => onDeleteAlarm(alarms.id)}>
                    Delete Alarm
                </button>
                </li>
            ))}
            </ul>
        </div>
      );
    }