(ns b-logic-basics.storage-test
  (:require [clojure.test :refer :all]
            [b-logic-basics.storage.core :as stg]
            [b-logic-basics.storage.in-memory :as mem]))

(defn is-valid-storage
  "Given an implementation of the Storage protocol,
  assert that it fulfills the contract."
  [stg]
  (let [obj {:this-is "some object"}
        id "id-of-some-object"]

    (testing "can store and retrieve a link"
      (testing "add-obj returns the id"
        (is (= id (stg/add-obj stg id obj)))

        (testing "and it won't overwrite an existing id"
          (is (nil? (stg/add-obj stg id {:bogus "object"})))
          (is (= obj (stg/get-obj stg id))))))

    (testing "can update an obj"
      (let [new-obj {:that-s "a new object"}]
        (stg/update-obj stg id new-obj)
        (is (= new-obj (stg/get-obj stg id)))))

    (testing "can delete an obj"
      (stg/delete-obj stg id)
      (is (nil? (stg/get-obj stg id))))

    (testing "can list all objs"
      (let [id-objs {"a" {:an "object can"}
                     "b" {:also "just be"}
                     "c" "a string :)"}
            ids (doseq [[id obj] id-objs]
                  (stg/add-obj stg id obj))
            objs (stg/list-objs stg)]

        (testing "in a map"
          (is (map? objs))

          (testing "equal to the objs we created"
            (is (= id-objs objs))))))))

(deftest in-memory-storage-test
  (let [stg (mem/in-memory-storage)]
    (is-valid-storage stg)))
