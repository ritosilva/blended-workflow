// verify that the operation is consistently defined
module filesystem/doctorappointment/complete/goalfinegrained/VerifyDefReportDescription

open filesystem/doctorappointment/complete/goalfinegrained/GoalSpecExec


assert DefReportDescriptionPreservesInv {
	all s, s': State, r: Report |
		Invariants [s] and defReportDescription [s, s', r] => Invariants [s']
}
check DefReportDescriptionPreservesInv for 4 but 18 State, 5 Int
