import { SELECT_SPECIFICATION, SELECT_INSTANCE, GET_ENTITY_INSTANCES, 
  SET_UNIT_OF_WORK, SET_SELECTED_ENTITY_INSTANCE, SET_ATTRIBUTE_INSTANCE_VALUE, 
  SET_LINK_ENTITY_INSTANCES, CREATE_ENTITY_INSTANCE, DELETE_ENTITY_INSTANCE } from "../constants/action-types";

const initialState = {
    spec: {},
    name: '',
    entityInstances: [],
    unitOfWork: []
  };

const rootReducer = (state = initialState, action) => {
    switch (action.type) {
      case SELECT_SPECIFICATION:
        return { ...state, spec: action.spec, name: '' };
      case SELECT_INSTANCE:
        return { ...state, name: action.name };
      case GET_ENTITY_INSTANCES:
        return { ...state, entityInstances: action.entityInstances };
      case SET_UNIT_OF_WORK:
        return { ...state, unitOfWork: action.unitOfWork };
      case SET_SELECTED_ENTITY_INSTANCE:
        return { ...state, unitOfWork: state.unitOfWork.map(etd => {
          if (etd.id === action.oldId) {
            return { ...etd, id: action.newId};
          } else {
            return etd;
          }
        }) };
      case SET_ATTRIBUTE_INSTANCE_VALUE:
        return { ...state, unitOfWork: state.unitOfWork.map(etd => {
          if (etd.id === action.entityInstance.id) {
            return { ...etd, 
              attributeInstances: etd.attributeInstances.map(ai => {
                if (ai.attribute.name === action.attributeInstance.attribute.name) {
                  return { ...ai, value: action.value };
                } else {
                  return ai;
                }
            })}
          } else {
            return etd;
          }
        })};
      case SET_LINK_ENTITY_INSTANCES:
        return { ...state, unitOfWork: state.unitOfWork.map(etd => {
          if (etd.id === action.entityInstance.id) {
            return { ...etd,
              links: etd.links.map(l => {
                if (l.mulCondition.rolename === action.mulCondition.rolename) {
                  return { ...l, entityInstances: action.entityInstances};
                } else {
                  return l;
                }
              }) } 
          } else {
            return etd;
          }
        }) };
      case CREATE_ENTITY_INSTANCE:
        return { ...state, unitOfWork: [ ...state.unitOfWork, action.entityInstance]};
      case DELETE_ENTITY_INSTANCE:
        return { ...state, unitOfWork: state.unitOfWork.filter(ei => ei.id !== action.id) 
        };
      default: 
        return state;
    }
};

export default rootReducer;