(ns b-logic-basics.storage.in-memory
  (:require [b-logic-basics.storage.core :refer :all]))

(defn add-obj*
  [!stg id obj]
  (when-not (contains? @!stg id)
    (swap! !stg assoc id obj)
    id))

(defn get-obj*
  [!stg id]
  (get @!stg id))

(defn update-obj*
  [!stg id obj]
  (when (contains? @!stg id)
    (swap! !stg assoc id obj)
    id))

(defn delete-obj*
  [!stg id]
  (swap! !stg dissoc id)
  nil)

(defn list-objs*
  [!stg]
  @!stg)

(defn in-memory-storage
  []
  (let [!stg (atom {})]
    (reify Storage
      (add-obj [_ id obj] (add-obj* !stg id obj))
      (get-obj [_ id] (get-obj* !stg id))
      (update-obj [_ id obj] (update-obj* !stg id obj))
      (delete-obj [_ id] (delete-obj* !stg id))
      (list-objs [_] (list-objs* !stg)))))
