// verify that the operation is consistently defined
module filesystem/doctorappointment/complete/goalcoarsegrained/VerifyProcessMedicalAppointment

open filesystem/doctorappointment/complete/goalcoarsegrained/GoalSpecExec


assert ProcessMedicalAppointmentPreservesInv {
	all s, s': State, p: Patient, e: Episode |
		Invariants [s] and processMedicalAppointment [s, s', p, e] => Invariants [s']
}
check ProcessMedicalAppointmentPreservesInv for 4 but 10 State, 5 Int
