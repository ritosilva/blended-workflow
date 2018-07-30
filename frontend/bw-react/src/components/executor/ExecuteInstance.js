import React from 'react';
import { Switch, Route, Link } from 'react-router-dom';
import { DataView } from './DataView';
import { GoalExecutor } from './GoalExecutor';
import { ActivityExecutor } from './ActivityExecutor';

export const ExecuteInstance = (props) => (
    <div> 
        <h5>Executor of instance {props.match.params.name}</h5>
        <ul>
            <li><Link to={`/specifications/${props.match.params.specId}/executor/instances/${props.match.params.name}/data`}>Data View</Link></li>
            <li><Link to={`/specifications/${props.match.params.specId}/executor/instances/${props.match.params.name}/goals`}>Goal View</Link></li>
            <li><Link to={`/specifications/${props.match.params.specId}/executor/instances/${props.match.params.name}/activities`}>Activity View</Link></li>
        </ul>

        <Switch>
            <Route path='/specifications/:specId/executor/instances/:name/data' component={DataView}/>
            <Route path='/specifications/:specId/executor/instances/:name/goals' component={GoalExecutor}/>
            <Route path='/specifications/:specId/executor/instances/:name/activities' component={ActivityExecutor}/>
        </Switch>
    </div>
)