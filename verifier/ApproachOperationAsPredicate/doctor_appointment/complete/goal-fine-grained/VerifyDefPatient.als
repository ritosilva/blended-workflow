// verify that operations are consistent
module filesystem/doctorappointment/complete/goalfinegrained/VerifyDefPatient

open filesystem/doctorappointment/complete/goalfinegrained/GoalSpecExec


assert DefPatientPreservesInv {
	all s, s': State | all p: Patient |
		Invariants [s] and defPatient [s, s', p] => Invariants [s']
}
check DefPatientPreservesInv for 4 but 18 State, 5 Int
