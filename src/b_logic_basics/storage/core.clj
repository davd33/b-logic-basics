(ns b-logic-basics.storage.core)

(defprotocol Storage
  (add-obj [this id obj]
    "Stores an obj and returns the id if success, nil if id already in use.")
  (get-obj [this id]
    "Returns an obj for a given ID. Returns nil if there's no associated obj.")
  (update-obj [this id new-obj]
    "Updates id to point to the new-obj. Returns ID if success, otherwise nil.")
  (delete-obj [this id]
    "Removes the association ID->obj if it exists. Returns nil.")
  (list-objs [this]
    "Returns a map of all known IDs to obj."))
