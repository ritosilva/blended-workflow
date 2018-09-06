import React from 'react';
import { connect } from 'react-redux';
import { RepositoryService } from '../../../services/RepositoryService';
import { GoalModelDiagram } from './GoalModelDiagram';

const mapStateToProps = state => {
    return { spec: state.spec };
};  

class ConnectedGoalModel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            goals: {}
        };
    }

    componentDidMount() {
        const service = new RepositoryService();

        service.getGoalModel(this.props.spec.specId).then(response => {
            this.setState({ dataModel: response.data }
            );
        });
    }

    render() {
        return (
            <div>
                <b>{this.props.spec.name}: Goal Model Diagram</b><br /><br />
                <GoalModelDiagram specId={this.props.spec.specId} />
            </div>
        )
    }
}

const GoalModel = connect(mapStateToProps)(ConnectedGoalModel);

export default GoalModel;