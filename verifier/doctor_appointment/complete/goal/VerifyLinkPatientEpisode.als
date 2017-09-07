// verify that operations are consistent
module filesystem/doctorappointment/complete/goal/VerifyLinkPatientEpisode

open filesystem/doctorappointment/complete/invariants
open filesystem/doctorappointment/complete/goal/GoalModel


assert LinkPatientEpisodePreservesInv {
	all s, s': State, p: Patient, e: Episode |
		Invariants [s] and linkPatientEpisode [s, s', p, e] => Invariants [s']
}
check LinkPatientEpisodePreservesInv for 4 but 2 State, 5 Int
