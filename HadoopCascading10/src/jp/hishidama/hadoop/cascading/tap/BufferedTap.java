package jp.hishidama.hadoop.cascading.tap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;

import cascading.scheme.Scheme;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntryCollector;
import cascading.tuple.TupleEntryIterator;

@SuppressWarnings("deprecation")
public class BufferedTap extends Tap {
	private static final long serialVersionUID = 1198236021687603151L;

	protected List<Tuple> list = null;
	protected long pathModified = 0;

	public BufferedTap(Scheme scheme) {
		super(scheme);
	}

	public BufferedTap(Scheme scheme, List<Tuple> list) {
		super(scheme);
		this.list = list;
		pathModified = System.currentTimeMillis();
	}

	public BufferedTap(Scheme scheme, List<Tuple> list, SinkMode sinkMode) {
		super(scheme, sinkMode);
		this.list = list;
		pathModified = System.currentTimeMillis();
	}

	@Override
	public Path getPath() {
		return new Path("list:///" + list);
	}

	@Override
	public boolean makeDirs(JobConf conf) throws IOException {
		if (list == null) {
			list = new ArrayList<Tuple>();
			pathModified = System.currentTimeMillis();
		}
		return true;
	}

	@Override
	public boolean deletePath(JobConf conf) throws IOException {
		list = null;
		return true;
	}

	@Override
	public boolean pathExists(JobConf conf) throws IOException {
		return list != null;
	}

	@Override
	public long getPathModified(JobConf conf) throws IOException {
		return pathModified;
	}

	@Override
	public void sourceInit(JobConf conf) throws IOException {
		// TODO
		// FileInputFormat.addInputPath(conf, getPath());
		// super.sourceInit(conf);
	}

	@Override
	public void sinkInit(JobConf conf) throws IOException {
		// TODO
		// FileOutputFormat.setOutputPath(conf, getPath());
		// super.sinkInit(conf);
	}

	@Override
	public TupleEntryIterator openForRead(JobConf conf) throws IOException {
		return new TupleEntryIterator(getSourceFields(), list.iterator());
	}

	@Override
	public TupleEntryCollector openForWrite(JobConf conf) throws IOException {
		return new TupleEntryCollector(getSinkFields()) {

			@Override
			protected void collect(Tuple tuple) {
				list.add(tuple);
				pathModified = System.currentTimeMillis();
			}
		};
	}

	public List<Tuple> getList() {
		return list;
	}
}
