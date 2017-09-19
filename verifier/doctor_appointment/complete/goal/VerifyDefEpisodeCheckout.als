// verify that the operation is consistently defined
module filesystem/doctorappointment/complete/goal/VerifyDefEpisodeCheckout
open filesystem/doctorappointment/complete/invariants
open filesystem/doctorappointment/complete/goal/GoalModel


assert DefEpisodeCheckoutPreservesInv {
	all s, s': State, e: Episode |
		Invariants [s] and defEpisodeCheckout [s, s', e] => Invariants [s']
}
check DefEpisodeCheckoutPreservesInv for 4 but 2 State, 5 Int