import { useState } from 'react';
import 'react-time-picker/dist/TimePicker.css';
import 'react-clock/dist/Clock.css';
import TimePicker from 'react-time-picker';

export default function SetClock({onAddAlarm}) {
    const [time,setTime] = useState('');
    const [text, setText] = useState(''); 

    return (
        <>
            <div style={{textAlign:"center"}}>
                <h1 className='mb-4'>Set a new Alarm clock</h1>
                <div className='mb-3'>
                <TimePicker
                    label="Controlled picker"
                    onChange={setTime} value={time}
                    format={"h:mm a"}
                />
                </div>
                <input 
                    style={{marginRight:"10px"}}
                    placeholder="Set Alarm"
                    value={text}
                    onChange={e => setText(e.target.value)}
                />
                <button 
                    disabled={time==='' || text.length===0}
                    onClick={() => {
                    setTime('');
                    setText('');
                    onAddAlarm(text, time);
                    }}>Add Alarm</button>
            </div>
        </>
    )
}