(ns doppelanger.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [doppelanger.core-test]
   [doppelanger.common-test]))

(enable-console-print!)

(doo-tests 'doppelanger.core-test
           'doppelanger.common-test)
