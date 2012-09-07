(ns casciidoc.test.core
  (:use [casciidoc.core] :reload-all)
  (:use [clojure.pprint])
  (:use [clojure.test]))

(defn asciidoc-rule 
  ([root-node text] 
         (let [r (asciidoc root-node text)]
;           (pprint r)
           (is (not (nil? r)))))
  ([root-node text ast]
    (let [r (asciidoc root-node text)]
      (is (= r ast)))))

(deftest catch-all-rule
         (asciidoc-rule :Document "ab\nc d\n" {:Document '([{:Paragraph {:Text ({:Char \a} {:Char \b})}} {:EOL \newline}] [{:Paragraph {:Text ({:Char \c} {:Whitespace \space} {:Char \d})}} {:EOL \newline}])}))

(deftest end-of-line
         (asciidoc-rule :EOL "\n" {:EOL \newline}))

(deftest any-text
         (asciidoc-rule :Text "hello shit" {:Text '({:Char \h} {:Char \e} {:Char \l} {:Char \l} {:Char \o} {:Whitespace \space} {:Char \s} {:Char \h} {:Char \i} {:Char \t})}))

(deftest paragraph
         (asciidoc-rule :Paragraph "hello shit\n")
         (asciidoc-rule :Paragraph "ab\n" {:Paragraph 
                                           {:Text '({:Char \a} 
                                                    {:Char \b})}}))

