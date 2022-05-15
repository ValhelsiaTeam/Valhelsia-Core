package net.valhelsia.valhelsia_core.client.util.combiner;

import java.util.List;

public interface BlockCombineResult<S>  {

    <T> T apply(BlockCombiner.Combiner<S, T> combiner);
    List<S> getValues();

    final class Single<S> implements BlockCombineResult<S> {
        private final S single;

        public Single(S single) {
            this.single = single;
        }

        public <T> T apply(BlockCombiner.Combiner<S, T> combiner) {
            return combiner.acceptSingle(single);
        }

        @Override
        public List<S> getValues() {
            return List.of(this.single);
        }
    }

    final class Double<S> implements BlockCombineResult<S> {

        private final S first;
        private final S second;

        public Double(S first, S second) {
            this.first = first;
            this.second = second;
        }

        public <T> T apply(BlockCombiner.Combiner<S, T> combiner) {
            return combiner.acceptDouble(this.first, this.second);
        }

        @Override
        public List<S> getValues() {
            return List.of(this.first, this.second);
        }
    }

    final class Multiple<S> implements BlockCombineResult<S> {
        private final List<S> list;

        public Multiple(List<S> values) {
            this.list = values;
        }

        public <T> T apply(BlockCombiner.Combiner<S, T> combiner) {
            return combiner.acceptMultiple(this.list);
        }

        @Override
        public List<S> getValues() {
            return this.list;
        }
    }
}
