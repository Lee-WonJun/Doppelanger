(ns doppelanger.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [doppelanger.core-test]))

(doo-tests 'doppelanger.core-test)

